abstract sig  User{
	credentials: one Credentials
}
sig Username{}
sig Password{}
sig Credentials
{
	username: one Username,
	password: one Password
}
fact NoLooseCredentials{	
Password=Credentials.password
Username=Credentials.username
User.credentials=Credentials
}
fact CredentialsArePersonal
{
no disj u1,u2:User | u1.credentials=u2.credentials
}
fact UsernameAreUnique //D2
{
no disj c1,c2:Credentials| c1.username=c2.username
}
sig City{}//for simplicity we consider city instead of area of duty
sig Suggestion{}

sig Citizen extends User{report: set Report}//TODO vincolo assignment
fact NoSameReportByASingleUser
{
all ci:Citizen| no disj re1,re2:Report| re1 in ci.report and re2 in ci.report and re1.assignment=re2.assignment
}
sig Authority extends User
{
	city: one City, //D4
	notifications: set Report,
	municipality : one Municipality
}
sig Municipality extends User
{
	city: one City,
	suggestions: set Suggestion
}

fact NoAuthorityOutOfBounds
{
all au:Authority| au.city=au.municipality.city
}
sig Report
{
	city: one City,
	assignment : one Assignment
}
fact AllReportAreAssociatedToACitizen
{
Report=Citizen.report
}
fact EveryRightAuthoritiesAreNotified //R3
{
all re:Report,au:Authority| (re.city=au.city) <=>re in au.notifications
}
abstract sig Status{}
one sig Pending extends Status{}
one sig Resolved extends Status{}
one sig Accepted extends Status{}
sig Assignment
{
	city: one City,
	authority : lone Authority,
	state: one Status
}
{
	(authority=none and state=Pending) or (authority!=none and state!=Pending)
	city=authority.city
}
fact AuthorityWorkingOnAtMostOneAssignmentAtTheSameTIme
{
all au:Authority | no disj as1,as2:Assignment | as1.authority=au and as2.authority=au and as1.state=Accepted and as2.state=Accepted
}
pred IsWorking[a:Authority]
{
one as1:Assignment| as1.authority=a and as1.state=Accepted
}
pred canTakeAssignment[a:Authority, as1:Assignment]
{
!IsWorking[a] and as1.state=Pending and a.city=as1.city
}
fact AllAssignmentAreRelatedToAtLeastOneReport
{
all as1:Assignment| some re:Report | re.assignment=as1
}
fact ReportAndAssignmentInSameCity
{
	all r:Report| r.city=r.assignment.city
}
fact SuggestionsOnlyWhenThereAreAssignments
{
all m:Municipality| (m.suggestions!=none implies (some a:Assignment|a.city=m.city))
}

fact RespectDutyOfCare
{
all au:Authority| !IsWorking[au] => (no re:Report| re in au.notifications and (some as1:Assignment| re.assignment=as1 and as1.state=Pending))
}
pred show(){#(Assignment)=10 and #(Authority)=2}

run show for 10
