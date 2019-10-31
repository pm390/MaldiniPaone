abstract sig User{}
sig Username{}
sig Password{} 
sig Credentials{
	username: one Username,
	password: one Password
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
	credentials : one Credentials
}
sig Municipality extends User
{
	credentials : one Credentials
}

pred show()
{}
run show for 4
