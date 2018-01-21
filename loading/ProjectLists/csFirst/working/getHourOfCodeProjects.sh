#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

#curl https://scratch.mit.edu/projects/177224273/remixes/ > HOC1.html
curl https://scratch.mit.edu/projects/56987758/remixes/ > HOC2.html
#curl https://scratch.mit.edu/projects/121180931/remixes/ > HOC3.html

#Print all Unique Projects to a .txt file
cat HOC*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > HOC.txt
awk '!seen[$0]++' HOC.txt > new.txt
mv -f new.txt HOC.txt
