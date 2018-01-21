#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

#curl https://scratch.mit.edu/projects/57386372/remixes/ > Animation1a.html
#curl https://scratch.mit.edu/projects/57387138/remixes/ > Animation1b.html
#curl https://scratch.mit.edu/projects/57392492/remixes/ > Animation1c.html
#curl https://scratch.mit.edu/projects/57395118/remixes/ > Animation1d.html
#curl https://scratch.mit.edu/projects/57397472/remixes/ > Animation1e.html
#curl https://scratch.mit.edu/projects/54549130/remixes/ > Animation2.html
#curl https://scratch.mit.edu/projects/54311004/remixes/ > Animation3a.html
#curl https://scratch.mit.edu/projects/55530406/remixes/ > Animation3b.html
#curl https://scratch.mit.edu/projects/55529870/remixes/ > Animation3c.html
#curl https://scratch.mit.edu/projects/55888024/remixes/ > Animation4.html
#curl https://scratch.mit.edu/projects/55656586/remixes/ > Animation5.html
#curl https://scratch.mit.edu/projects/55889528/remixes/ > Animation6.html
#curl https://scratch.mit.edu/projects/55902576/remixes/ > Animation7.html
#curl https://scratch.mit.edu/projects/55359646/remixes/ > Animation8.html

#Print all Unique Projects to a .txt file
cat Animation*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Animation.txt
awk '!seen[$0]++' Animation.txt > new.txt
mv -f new.txt Animation.txt
