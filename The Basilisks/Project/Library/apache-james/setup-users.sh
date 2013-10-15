#!/bin/sh
sh james-cli.sh -h localhost adddomain thebasilisks.com
sh james-cli.sh -h localhost adddomain testmail.com
sh james-cli.sh -h localhost adduser jobs@thebasilisks.com jobs
sh james-cli.sh -h localhost adduser no_reply@thebasilisks.com noreply
sh james-cli.sh -h localhost adduser alex@testmail.com alex
sh james-cli.sh -h localhost adduser derek@testmail.com derek
sh james-cli.sh -h localhost adduser stanley@testmail.com stanley
