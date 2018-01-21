#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

curl https://scratch.mit.edu/projects/168477923/remixes/ > Random1.html
#curl https://scratch.mit.edu/projects/10015059/remixes/ > Random2.html
curl https://scratch.mit.edu/projects/10128067/remixes/ > Random3.html
#curl https://scratch.mit.edu/projects/11806234/remixes/ > Random4.html
#curl https://scratch.mit.edu/projects/11640429/remixes/ > Random5.html
#curl https://scratch.mit.edu/projects/12778537/remixes/ > Random6.html
#curl https://scratch.mit.edu/projects/10128431/remixes/ > Random7.html
#curl https://scratch.mit.edu/projects/10128515/remixes/ > Random8.html
#curl https://scratch.mit.edu/projects/154264862/remixes/ > Random9.html
#curl https://scratch.mit.edu/projects/10015060/remixes/ > Random10.html
#curl https://scratch.mit.edu/projects/192335511/remixes/ > Random11.html
#curl https://scratch.mit.edu/projects/193392276/remixes/ > Random12.html
#curl https://scratch.mit.edu/projects/183698088/remixes/ > Random13.html
#curl https://scratch.mit.edu/projects/192335511/remixes/ > Random14.html
#curl https://scratch.mit.edu/projects/191293253/remixes/ > Random15.html
#curl https://scratch.mit.edu/projects/12785898/remixes/ > Random16.html
#curl https://scratch.mit.edu/projects/94385536/remixes/ > Random17.html
#curl https://scratch.mit.edu/projects/11872281/remixes/ > Random18.html
#curl https://scratch.mit.edu/projects/24872474/remixes/ > Random19.html
#curl https://scratch.mit.edu/projects/11656680/remixes/ > Random20.html

#Print all Unique Projects to a .txt file
cat Random*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Random.txt
awk '!seen[$0]++' Random.txt > new.txt
mv -f new.txt Random.txt
