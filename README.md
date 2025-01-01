[Sandbox Job Application Key Details.md](https://github.com/user-attachments/files/18285691/Sandbox.Job.Application.Key.Details.md)
<h1>Sandbox Job Application System</h1>
<h2>Full Stack, Java with an Oracle Database</h2>

**Sandbox Job Application Key Details**

**Functionalities**

1. Create and login an account  
   1. A user can request to register their company, sent through the email of the creators of the website (takes 2-3 days). Upon verification, user is granted a company mode within the website.  
2. Company Mode  
   1. Allows the company to post and delete jobs  
   2. See all applicants  
   3. Reject or accept applicants  
   4. Set their own profile picture through a third party (i.e. imgur.com)  
3. Create a resume  
   1. Users are currently unable to upload a file for their resume, instead;  
   2. They are given a form in which they can enter information to create their resume.  
4. Update a resume  
   1. Using the same form, they are able to edit the details  
5. Users can search jobs  
   1. Inputting any details about a job they want, a list will appear matching these queries.  
6. Users can search other users  
   1. Yes

**Tables**

1. Users  
2. Company  
3. Applications  
4. Resume  
5. Skills  
6. Contact  
7. Company contact  
8. Job posts

![][image1]  
**Database Schema**

**Key concepts**  
**Java OOP**  
	**Objects** \- objects (classes) are data types, similar to integer, boolean and strings.  
		I.e. String variableName \= “value”; is similar to:  
		      ClassName variableName \= new ClassName(parameters);  
		      (Data type) (variable name) \= (value)  
	Methods can also be created specific to that class.  
		I.e.   
public class ClassName function() {  
			Block of code  
		}  
	Methods can be created without the class, however, using this method ensures more organization in code and provides more scalability as data management within the code becomes easier.

**Connection to Database**  
New user created (while using SYS as SYSDBA role; admin privileges). Username is sandbox, password is sandboxUser. This is required and advised rather than using SYS profile to connect probably because of security reasons, it is not good for an application to have many permissions. 

When trying to connect, if using SYS, Oracle requires you to login as SYSDBA, which does not seem possible. Instead, and the better way is to create a new user and grant it specific permissions.

The schema used and where the tables are contained is the C\#\#STUDENT\_PROJECT schema. Tables within this schema are called with: C\#\#STUDENT\_PROJECT.table\_name  
![][image2]

![Diagram Job Application (11)](https://github.com/user-attachments/assets/667ca38e-192f-4b4b-9888-02bdfe73c107)

![javaw_p5C4Tfg5a4](https://github.com/user-attachments/assets/75539646-0ebe-4379-9086-7ee5900c3f39)



<h3>This contains the following technologies:</h3>
<ul>
  <li>Java</li>
  <li>Oracle Database 23ai (Using docker registry)</li>
  <li>JSP</li>
  <li>Database hosted on docker</li>
  <li>DBeaver for Database UI</li>
</ul>

Resources:
<ul>
  <li>Oracle Database: https://www.oracle.com/ph/database/free/get-started/</li>
  <li>Docker: https://www.docker.com/</li>
  <li></li>
</ul>
