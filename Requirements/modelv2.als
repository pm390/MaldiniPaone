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

sig Statistic
{
	assignment: set Assignment
}
{
no a:assignment|a.state!=Ended
all disj a1,a2:assignment| a1.city=a2.city
assignment!=none //no void static
}

sig City{}//for simplicity we consider city instead of area of duty
sig Suggestion{}

sig Citizen extends User
{
report: set Report,
gps: lone City,
manualInput: lone City
}
{
gps=manualInput or gps=none or manualInput=none
}
fun FindCitizen [c:Citizen] : one City
{
c.gps+c.manualInput
}
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
fact AllCityAssociatedToAnEntity
{
	all c:City| c in Municipality.city or c in Report.city
}
fact AllSuggestionsAreSentToAMunicipality
{
all su:Suggestion| some mu:Municipality| su in mu.suggestions
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

abstract sig Status{}
one sig Pending extends Status{}
one sig Ended extends Status{}
one sig Accepted extends Status{}
sig Assignment
{
	city: one City,
	authority : lone Authority,
	state: one Status
}
fact StateMeaning{
all as1:Assignment|	((as1.authority=none and as1.state=Pending) or (as1.authority!=none and as1.state!=Pending and as1.city=as1.authority.city))
}
//General Facts
fact AuthorityWorkingOnAtMostOneAssignmentAtTheSameTIme
{
all au:Authority | no disj as1,as2:Assignment | as1.authority=au and as2.authority=au and as1.state=Accepted and as2.state=Accepted
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

//Predicates
pred IsWorking[a:Authority]
{
one as1:Assignment| as1.authority=a and as1.state=Accepted
}

fact NoLooseCredentials{	
Password=Credentials.password and #(Credentials)=#(User) and #(Username)=#(User)
}
fact CredentialsArePersonal
{
no disj u1,u2:User | u1.credentials=u2.credentials
}

//Domain Assumption
fact UsernameAreUnique //D2
{
no disj c1,c2:Credentials| c1.username=c2.username
}
fact RespectDutyOfCare //D7 if there are no Pending Assignments authority can't work on an assignment
{
all au:Authority| !IsWorking[au] => (no re:Report| re in au.notifications and (some as1:Assignment| re.assignment=as1 and as1.state=Pending))
}

fact AllCitizenLocationAreAvailableAndCorrect
{
all c:Citizen| FindCitizen[c]!=none and (all re:c.report | re.city=FindCitizen[c])
}


fact EveryRightAuthoritiesAreNotified //R3
{
all re:Report,au:Authority| (re.city=au.city) <=>re in au.notifications
}

fact StatisticsAreAlwaysUpdated
{
all as1:Assignment| (as1.state=Ended)<=>(some s:Statistic| as1 in s.assignment)
}

//Goals
assert AuthorityMustTakeAssignments 
{
all a:Authority|  (IsWorking[a]) or (no as1:Assignment| as1.city=a.city and as1.state=Pending)
}


pred show(){}
//pred show(){#(Assignment)=10 and #(Authority)=2 and #(City)=6}

//check AuthorityMustTakeAssignments for 10

pred world1() //Credentials are unique and every User is associated with a City(which reprents his/her position)
{
#(Assignment)=0
#(Report)=0
#(Citizen)>0
#(Authority)>0
#(Municipality)>0
}
//run world1 for 5

pred world2() //same assignment can be derived from reports from different Citizens 
{
#(Assignment)=2
#(Statistic)=0
#(Report)=4
#(Authority)=1
#(City)=1
some as1:Assignment |as1.state=Pending
}
//run world2 for 8

pred world3() //a more generic world with all possible states of assignments
{
#(Assignment)=4
#(Authority)=2
some as1:Assignment |as1.state=Ended
some as1:Assignment |as1.state=Pending
some as1:Assignment |as1.state=Accepted
}
run world3 for 5


