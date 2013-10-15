@echo off
james-cli -h localhost adddomain thebasilisks.com
james-cli -h localhost adddomain testmail.com
james-cli -h localhost adduser jobs@thebasilisks.com jobs
james-cli -h localhost adduser no_reply@thebasilisks.com noreply
james-cli -h localhost adduser alex@testmail.com alex
james-cli -h localhost adduser derek@testmail.com derek
james-cli -h localhost adduser stanley@testmail.com stanley
pause