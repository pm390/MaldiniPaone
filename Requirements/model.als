abstract sig User{
	credentials: one Credentials
}
sig Username{}
sig Password{} 
sig Credentials{
	username: one Username,
	password: one Password
}
sig  Location //we represent areas instead of precise points
{
}
fact UnivoqueCredentials
{
all disj c,c1:Credentials| c.username!=c1.username
}
sig Citizen extends User
{
}
sig Authority extends User
{
	location: one Location,
	authored : one Municipality
}
sig Municipality extends User
{
	location: one Location
}
fact NoAuthorityOutOfMunicipalityBounds
{
all au:Authority | au.location=au.authored.location
}
fact OnlyOneUserForAccount// only one user for each credential
{
all disj u,u1:User|  u.credentials!=u1.credentials
all c:Credentials| c in User.credentials //all credentials associated to a User
}
abstract sig Status{}
sig Pending extends Status{}
sig Accepted extends Status{}
sig Resolved extends Status{}

sig Report
{
	location: one Location,
	taken_care_by: lone Authority,
	state: one Status
}
{
 	(state = Pending and taken_care_by = none) or ((state = Accepted or state = Resolved) and taken_care_by! = none)
}
sig Suggestion
{
	municipality: one Municipality
}
fact SuggestionGeneratedWhenThereAreLotOfViolations //Reports of violations //TODO fix this costraint
{
	all m:Municipality,s:Suggestion| some r:Report| ((s.municipality=m) implies (r.location=m.location))
}
pred show(){}
run show for 6
//TODO check what is wrong


