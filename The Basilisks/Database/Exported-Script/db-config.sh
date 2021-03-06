#!/bin/bash
echo "***************************************************"
echo "*                 THE BASILISKS                   *"
echo "*             HR OPERATIONS MANAGER               *"
echo "***************************************************"
echo "*            Database  Configuration              *"
echo "***************************************************"
echo
echo "CREATING DATABASE"
echo "---------------------------------------------------"
echo "This will take a few minutes. Please wait . . ."
echo
echo
db2 -tf DDL1.sql
cd DB
echo
echo
echo "POPULATING DATABASE WITH SAMPLE VALUES"
echo "---------------------------------------------------"
echo
db2move HROPS load
cd ..
echo
echo
echo "SETTING UP FOREIGN KEYS"
echo "---------------------------------------------------"
echo
db2 -tf DDL2.sql
echo
echo "---------------------------------------------------"
echo "Database Configuration Completed!!"
echo "---------------------------------------------------"
