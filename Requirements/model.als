abstract sig User{
	credentials: one Credentials
}
sig Username{}
sig Password{} 
sig Credentials{
	username: one Username,
	password: one Password
}
fact AllCredentialsAreAssociateToAUser
{
all c:Credentials| c in User.credentials 
}
sig  Location //we represent areas instead of precise points //TODO think if it is better to call it city
{}

sig Citizen extends User
{}

sig Authority extends User
{
	location: one Location, //Domain Assumpion , position is known 
	authored : one Municipality
}

sig Municipality extends User
{
	location: one Location
}

sig Suggestion
{
	municipality: one Municipality
}

fact NoAuthorityOutOfMunicipalityBounds
{
all au:Authority | au.location=au.authored.location
}

abstract sig Status{}
sig Pending extends Status{}
sig Accepted extends Status{}
sig Resolved extends Status{}

sig Assignment
{
	location: one Location,
	taken_care_by: lone Authority,
	state: one Status
}
{
 	(state = Pending and taken_care_by = none) or ((state = Accepted or state = Resolved) and taken_care_by! = none)
}

sig Notification { 
	assignment: one Assignment,
	receivers: set Authority
}



fact AuthorityTakesCareOfOneAssignmentAtTheTime
{
	all a:Authority|no disj a1,a2:Assignment| a1.taken_care_by=a and a2.taken_care_by=a
}
fact AssignmentTakenCareByANotifiedAuthority
{
all a:Assignment|a.taken_care_by=none or (one n:Notification|n.assignment=a and a.taken_care_by in n.receivers)
}
pred AuthorityIsWorking[ a:Authority]
{
a in Assignment.taken_care_by
}
pred canTakeAssignment[a:Authority, as1:Assignment]
{
! AuthorityIsWorking[a] and as1.state=Pending //TODO add is notified???
}


//Domain Assumpitions
fact AuthorityRespectDutyOfCare	//D7 if there are no Pending Assignments authority can't work on an assignment
{
all a:Authority| (no as1:Assignment| as1.location=a.location and as1.state=Pending) => !AuthorityIsWorking[a]  
}
fact UsernameAreUnique	// D2 only one user for each credential
{
all disj u,u1:User|  u.credentials.username!=u1.credentials.username
}




//Requirements
fact OnlyRightAuthorityNotified		//R3
{
all n:Notification| n.assignment.location=n.receivers.location
}


fact SuggestionGeneratedOnlyWhenThereAreViolations //Reports of violations
{
	all m:Municipality,s:Suggestion| ((s.municipality=m) implies (some a:Assignment| (a.location=m.location)))
}

//Goals 
assert AuthorityCanTakeAssignments
{
all a:Authority,as1:Assignment | ((as1.state=Pending) and (a.location=as1.location) and (!AuthorityIsWorking[a]) )implies canTakeAssignment[a,as1] 
}




pred show(){}
check  AuthorityCanTakeAssignments for 6
run show for 6
//TODO check what is wrong


