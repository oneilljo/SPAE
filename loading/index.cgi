#!/usr/bin/env perl

 
#-------------------------------------------------------------
# Program Name:	progress.cgi
# Author:	Joseph O'Neill
# Date:		June 2018
# Description:	A simple script to parse a user's SCRATCH 
#               project and display their work in an organized
#               and detail manner.
#-------------------------------------------------------------

use strict;
use warnings;

use CGI;
use Time::Local;

#-------------------------------------------------------------
# GLOBAL VARIABLES
#
  
my $cgi = CGI->new;

my $projectURL = $cgi->param("projectURL");
$projectURL =~ /^(?:(?:http(?:s)??:\/\/)?scratch\.mit\.edu\/projects\/)?([0-9]+)\/?(.+)?$/;

my $projectID = $1;

#
# END GLOBAL VARIABLES
#-------------------------------------------------------------
# MAIN
#

print_header();
print_body();
print_footer();

download();

exit(0);
#
# END MAIN

#-------------------------------------------------------------
sub print_header
# Begin printing printing the webpage. Feed the browser the
# http content-type. Open the html tag. Set the title of the 
# page. Declare the content-type. Load the header of the site
# as well as the favicon and background image.
#-------------------------------------------------------------
{
  # Head for HTML

  print CGI::header();

  print "<html>";
  print "  <head>";
  print "    <title>SCRATCH Thesis Project</title>";
  print "    <meta charset=\"UTF-8\">";
  print "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">";
  print "    <link rel=\"stylesheet\" href=\"../css/format.css\">";
  print "    <link rel=\"stylesheet\" href=\"../css/font.css\">";
  print "    <link rel=\"stylesheet\" href=\"../css/style.css\">";
  print "    <link rel=\"shortcut icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">";
  print "    <link rel=\"icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">";
  print "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>";
  print "  </head>";

  # Header to to page and background image.
  print "  <body onload=\"document.download.submit()\">";
  print "    <div class=\"w3-display-container bgimg w3-text-white\">";
  print "      <div class\"w3-display-topright w3-padding-small w3-medium\">";
  print "        <a href=\"../\">Home</a> | <a href=\"../about/\">About</a> | <a href=\"../login/\">Login</a> | <a href=\"../signup/\">Sign Up</a>";
  print "      </div>";
}
#-------------------------------------------------------------
sub print_body
# Print the first visible part of the page. Open the body tag.
# Print the page heading. If the user is an admin, state it.
#-------------------------------------------------------------
{
  print "    <div class=\"w3-display-middle w3-center\">";
  print "      <img class= \"w3-animate-top\" src=\"../images/scratch.png\" alt=\"Title\">";
  print "      </br>";
  print "      <label class=\"w3-animate-bottom w3-padding-small\" for=\"download\">Downloading and processing your project...</label>";
  print "      </br>";
  print "      </br>";
  print "      <img class=\"w3-animate-bottom\" style=\"width:50px;height:50px;\"  src=\"../images/loading.gif\" alt=\"Loading\">";
  print "      <form name=\"download\" method=\"post\" action=\"/scratch/progress/index.cgi\">";
  print "        <input type=\"hidden\" name=\"projectID\" id=\"projectID\" value=\"${projectID}\" />";
  print "      </form>";
  print "    </div>";
}
#-------------------------------------------------------------
sub print_footer
# Prints the bottom portion of the webpage. Print a horizontal
# rule to signify the end of the page. Close the body tag and
# close the html tag.
#-------------------------------------------------------------
{
  print "      <div class=\"w3-display-bottomleft w3-padding-small w3-tiny\">";
  print "      Created & Mantained by Joseph O'Neill. Website Repository can be found <a href=\"https://github.com/oneilljo/SCRATCH\" target=\"_blank\">here</a>.";
  print "      </div>";
  print "    </div>";
  print "  </body>";
  print "</html>";
}
#-------------------------------------------------------------
sub download
# Downloads the project on the system side, refreshing the 
# loaded page on completion.
#-------------------------------------------------------------
{
  system("./projectdownloader.py ${projectID}");
  system("chmod 777 project-${projectID}.sb2");
  system("chown apache:apache project-${projectID}.sb2");
  system("cp project-${projectID}.sb2 ../savedProjects");
  system("mv -f project-${projectID}.sb2 ../SCATT/submissions/project-${projectID}.sb2");
  system("/usr/bin/java -jar ../SCATT/Scatt.jar");
  system("rm -f ../SCATT/submissions/project-${projectID}.sb2");
  system("rm -rf zips/${projectID}.zip");
  system("mv unzips/project-${projectID}/project.json ../savedReports/project-${projectID}");
  system("rm -rf unzips/${projectID}");
}
#-------------------------------------------------------------
