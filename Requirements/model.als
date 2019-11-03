abstract sig User{}
sig Username{}
sig Password{} 
sig Credentials{
	username: one Username,
	password: one Password
}

sig  Location
{
	long: one Int,
	lat: one Int
}
{	//for simplicity we use -6,6,-3,3 as bounds instead of -180,180,-90,90
	long>=-6 and long<=6 and lat>=-3 and lat<=3
}
fact UnivoqueCredentials
{
all disj c,c1:Credentials| c.username!=c1.username
}
sig Citizen extends User
{
	credentials: lone Credentials
}

sig Authority extends User
{
	credentials : one Credentials,
	location: one Location
}
sig Municipality extends User
{
	credentials : one Credentials,
	location: one Location
}

fact OnlyOneUserForAccount//both only one account type for each credential and one user of that type for each credential
{
all c:Credentials | let ci= c in Citizen.credentials | let au= c in Authority.credentials| let mu = c in Municipality.credentials|
//check if only one kind of user has those credentials
	(((ci and not (au or mu))or
	(au and not(ci or mu))or
	(mu and not (au or ci))))
//only one user for each credential
all c:Credentials |one  citizen:Citizen | citizen.credentials=c  or
			   one   authority:Authority | authority.credentials=c or
			   one   municipality:Municipality | municipality.credentials=c
}
abstract sig Status{}
sig Pending extends Status{}
sig Accepted extends Status{}
sig Resolved extends Status{}

sig report
{
	location: one Location,
	taken_care_by: lone Authority,
	state: one Status
}
{
 	(state=Pending and taken_care_by=none) or ((state=Accepted or state=Resolved) and taken_care_by!=none)
	#taken_care_by=1

}
pred show(){}
run show for 4


