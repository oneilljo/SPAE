#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

curl https://scratch.mit.edu/projects/24209090/remixes/ > Art1.html
curl https://scratch.mit.edu/projects/23956837/remixes/ > Art2a.html
curl https://scratch.mit.edu/projects/23957153/remixes/ > Art2b.html
curl https://scratch.mit.edu/projects/23957767/remixes/ > Art2c.html
curl https://scratch.mit.edu/projects/23956722/remixes/ > Art2d.html
curl https://scratch.mit.edu/projects/99764403/remixes/ > Art2e.html
curl https://scratch.mit.edu/projects/99765138/remixes/ > Art2f.html
curl https://scratch.mit.edu/projects/120615580/remixes/ > Art2g.html
curl https://scratch.mit.edu/projects/24818944/remixes/ > Art3a.html
curl https://scratch.mit.edu/projects/24454633/remixes/ > Art3b.html
curl https://scratch.mit.edu/projects/24820113/remixes/ > Art3c.html
curl https://scratch.mit.edu/projects/25014570/remixes/ > Art3d.html
curl https://scratch.mit.edu/projects/27432322/remixes/ > Art4.html
curl https://scratch.mit.edu/projects/25468311/remixes/ > Art5.html
curl https://scratch.mit.edu/projects/25139098/remixes/ > Art6.html
curl https://scratch.mit.edu/projects/24456318/remixes/ > Art7.html
#No Starter project for Art8.html. Need to find a better solution here...

#Print all Unique Projects to a .txt file
cat Art*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Art.txt
awk '!seen[$0]++' Art.txt > new.txt
mv -f new.txt Art.txt
