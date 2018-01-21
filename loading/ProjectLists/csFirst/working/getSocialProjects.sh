#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

#curl https://scratch.mit.edu/projects/31037776/remixes/ > Social1.html
#curl https://scratch.mit.edu/projects/31043354/remixes/ > Social2.html
#curl https://scratch.mit.edu/projects/31053902/remixes/ > Social3.html
curl https://scratch.mit.edu/projects/31557332/remixes/ > Social4.html
curl https://scratch.mit.edu/projects/33728964/remixes/ > Social5.html
#curl https://scratch.mit.edu/projects/37444124/remixes/ > Social6.html
#curl https://scratch.mit.edu/projects/31213422/remixes/ > Social7.html
#curl https://scratch.mit.edu/projects/31549260/remixes/ > Social8.html

#Print all Unique Projects to a .txt file
cat Social*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Social.txt
awk '!seen[$0]++' Social.txt > new.txt
mv -f new.txt Social.txt
