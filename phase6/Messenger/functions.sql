CREATE OR REPLACE Procedure add_user(UserName varchar, FirstName varchar, LastName varchar, PhoneNumber varchar, Bio text)
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	INSERT INTO Users VALUES
	(UserName, FirstName, LastName, PhoneNumber, Bio);
END;
$$
;

CREATE OR REPLACE Procedure Delete_user(TargetUserName varchar)
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	Delete From Users
	Where UserName = TargetUserName;
END;
$$
;

CREATE OR REPLACE Procedure Change_bio(TargetUserName varchar, newBio text)
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	Update Users
	set Bio = newBio
	where UserName = TargetUserName;
END;
$$
;

CREATE OR REPLACE Procedure add_Chat(TargetTitle varchar)
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	Insert into Chat(Title, CreatedAt) Values
	(TargetTitle, CURRENT_TIMESTAMP);
END;
$$
;

CREATE OR REPLACE Procedure Join_Chat(ChatID int, username varchar, isAdmin boolean)
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	Insert into "Member" Values
	(username, chatID, isAdmin);
END;
$$
;

CREATE OR REPLACE Procedure Send_Message(TargetDestination int, Sender varchar, TargetContent BYTEA, textMessage text)
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	if (Sender in (select "user" from "Member"
		where "Member".chat = TargetDestination)) THEN
		Insert into Messages("content", textMessage, Sender, Destination, sendAt) Values
		(TargetContent, textMessage, Sender, TargetDestination, CURRENT_TIMESTAMP);
	END IF;
END;
$$
;

CREATE OR REPLACE Procedure Edit_Message(TargetMessageID int, newText text)
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	Update Messages
	set textMessage = newText
	where MessageID = TargetMessageID;
END;
$$
;

CREATE OR REPLACE Procedure Delete_Message(TargetMessageID int)
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	Delete From Messages
	Where MessageID = TargetMessageID;
END;
$$
;

CREATE OR REPLACE FUNCTION Get_all_messages_of_user(TargetUserName varchar) 
    RETURNS TABLE (
        notText BYTEA,
        caption text
) 
AS $$
BEGIN
    RETURN QUERY SELECT
        "content", textMessage
    FROM Messages
    WHERE Sender = TargetUserName;
END; $$ 

LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION Get_number_messages_of_user(TargetUserName varchar) 
    RETURNS integer
AS $$
BEGIN
    RETURN count(MessageID)
    FROM Messages
    WHERE Sender = TargetUserName;
END; $$ 

LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION Get_avg_number_messages_of_user(TargetUserName varchar) 
    RETURNS float
AS $$
DECLARE 
	num_target integer;
	num_total integer;
BEGIN
    num_target := count(MessageID) FROM Messages WHERE Sender = TargetUserName;
	num_total := count(MessageID) FROM Messages;
	RETURN cast(num_target as decimal) / num_total;
END; $$ 

LANGUAGE 'plpgsql';