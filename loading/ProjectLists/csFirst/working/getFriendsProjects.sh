#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

curl https://scratch.mit.edu/projects/34370012/remixes/ > Friends1.html
curl https://scratch.mit.edu/projects/34350450/remixes/ > Friends2.html
curl https://scratch.mit.edu/projects/34352356/remixes/ > Friends3.html
curl https://scratch.mit.edu/projects/33518424/remixes/ > Friends4.html
curl https://scratch.mit.edu/projects/100064303/remixes/ > Friends5.html
curl https://scratch.mit.edu/projects/33384474/remixes/ > Friends6.html
curl https://scratch.mit.edu/projects/34352812/remixes/ > Friends7.html
curl https://scratch.mit.edu/projects/30549980/remixes/ > Friends8.html

#Print all Unique Projects to a .txt file
cat Friends*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Friends.txt
awk '!seen[$0]++' Friends.txt > new.txt
mv -f new.txt Friends.txt
