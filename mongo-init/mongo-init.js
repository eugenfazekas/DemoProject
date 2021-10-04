db.createUser(
	{
		user: "John_Woo",
		pwd:  "2hJ$lDS#nA&8",   
		roles: [
					{
						role: "readWrite",
						db: "db_nx"
					}
				]
	}
)
