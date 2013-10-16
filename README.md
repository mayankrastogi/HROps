THE BASILISKS
=============
HR Operations Manager
=====================


Installation Instructions
-------------------------
<pre>

1. Install RAD 7.5 on the system.
2. Be sure that you have checked "WAS 7.0 environment" option during installation of RAD 7.5
3. Install DB2 9 in the system.
4. Create Database:

Linux:
a. Option 1: Restore from Backup
i. Unzip the database from Linux.zip
ii. Type db2 in the terminal.
iii. Now run the following query:

RESTORE DATABASE HROPS FROM "<Path to Linux directory>" WITHOUT PROMPTING;

b. Option 2: Using DDL script
i. Open terminal and change current directory to Exported-Script directory.
ii. Type the following:
sh db-config.sh
iii. Creation of database will take some time. So be patient.

Windows:
a. Option 1: Restore from Backup
i. Unzip the database from Windows.zip
ii. Open DB2 Command Line Processor.
iii. Now run the following query:

RESTORE DATABASE HROPS FROM "<Path to Linux directory>" WITHOUT PROMPTING;

b. Option 2: Using DDL script
i. Browse to Exported-Script directory.
ii. Execute (Double-click) on:
db-config.bat
iii. Creation of database will take some time. So be patient.



5. Setup Apache James Email Server (Optional):

If you already have an IMAP supporting mail server, then this step is optional. However, you will have to configure your mail server accordingly.

i. Browse to Project/Library/apache-james
ii. Unzip apache-james.
iii. Copy setup-users.sh (if on Linux), or setup-users.bat (if on Windows) to bin folder of your copy of apache-james.
iv. Run the shell script (if on Linux), or batch file (if on Windows).

This will add test domains and test users to your copy of apache james.
You may use Pooka Email Client(included) to check or send mails.

6. Import HROpsEAR.ear from Projects folder to your RAD workspace. (Or you may unzip our workspace and import it).
7. Expand the HROps tree to the following directory:
HROps > WebContent > WEB-INF > config
8. Open DBConnection.properties file.
9. Change the username, password, host, port, and dbname, if needed. Make sure that these settings match your DB2 configuration. *IMPORTANT*
      (Other settings can be changed when you login as administrator)
10. Right-click on WebSphere Application Server v7.0 from Servers tab.
11. Click Add and Remove Projects.
12. Add Add HROpsEAR to configured projects.
13. Start the Application Server.
14. Once the server is started, open the application in your browser(with its context-root as /HROps).
15. Login as admin. (Login information given at the end)
16. Move your mouse over Select Task and click Server Configuration.
17. Configure your IMAP and SMTP server as needed. You should specify the email id on which applicants will send their resume in IMAP configuration.
18. The application is now ready to use. Refer to user manual for learn how to use the software.


</pre>
Default users and their passwords
---------------------------------
<pre>
Role               Employee-ID               Password
----               -----------               --------

ADMIN              25                        admin
HR                 26                        hr
MANAGER            27                        manager
INTERVIEWER        29                        interviewer

</pre>
