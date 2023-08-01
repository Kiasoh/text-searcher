CREATE TABLE IF NOT EXISTS "File" (
	FileID serial UNIQUE PRIMARY KEY,
	"content" bytea,
	fileName varchar(50)
);
CREATE SEQUENCE if not exists FIle_FileID
    start 1
    increment 1
    no maxvalue;

CREATE TABLE IF NOT EXISTS Users (
	UserName varchar(30) unique Primary key,
	FirstName varchar(30),
	LastName varchar(30),
	PhoneNumber varchar(12),
	Bio text,
	"password" text,
	ProfilePhotoID int REFERENCES "File"(FileID)
);
	
CREATE TABLE if not exists Chat (
	ChatID serial primary key,
	Title varchar(30),
	CreatedAt timestamp,
	ProfilePhotoID int REFERENCES "File"(FileID),
	"Type" varchar(15 ) CHECK("Type" in ('pv', 'channel', 'group'))
);
CREATE SEQUENCE if not exists Chat_ChatID
    start 1
    increment 1
    no maxvalue;
	
CREATE TABLE IF NOT EXISTS Messages (
	MessageID serial unique primary key,
	"File" int REFERENCES "File"(FileID),
	textMessage text,
	Sender varchar(30) references Users(UserName),
	Destination serial references Chat(ChatID),
	sendAt timestamp
);
CREATE SEQUENCE if not exists Message_MessageID
    start 1
    increment 1
    no maxvalue;
	
create table "Member" (
	"user" varchar(30) references Users(UserName),
	chat int references Chat(ChatID),
	isAdmin boolean,
	lastSeenMessage int
);


