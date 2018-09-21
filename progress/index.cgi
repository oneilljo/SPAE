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

my $projectID = $cgi->param("projectID");
my $file = "../analyzedReports/project-${projectID}.txt";

#
# END GLOBAL VARIABLES
#-------------------------------------------------------------
# MAIN
#

print_header();
print_body();
print_footer();

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
  print "    <title>A SCRATCH Thesis Project</title>";
  print "    <meta charset=\"UTF-8\">";
  print "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">";
  print "    <link rel=\"stylesheet\" href=\"../css/format.css\">";
  print "    <link rel=\"stylesheet\" href=\"../css/font.css\">";
  print "    <link rel=\"stylesheet\" href=\"../css/style.css\">";
  print "    <link rel=\"shortcut icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">";
  print "    <link rel=\"icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">";
  print "  </head>";

  # Header to to page and background image.
  print "  <body>";
  print "    <div class=\"w3-display-container bgimg w3-text-white\">";
  print "      <div class=\"w3-display-topleft w3-padding-large\">";
  print "        <a href=\"/scratch\"><img class=\"w3-animate-left\" src=\"../images/scratch.png\" alt=\"Title\" style=\"width:35%\"></a>";
  print "      </div>";

}
#-------------------------------------------------------------
sub print_body
# Print the first visible part of the page. Open the body tag.
# Print the page heading. If the user is an admin, state it.
#-------------------------------------------------------------
{

  print "      <div class=\"w3-display-centerleft w3-animate-left w3-margin-left w3-small\" style=\"width:35%; position:absolute;\">";
  print "        <a href=\"/scratch/\">Go Back to Homepage</a>";
  print "        <br>";
  print "          <a href=\"../analyzedReports/project-${projectID}.txt\">Link to Raw Project Report</a>";
  print "          <br>";
  print "          <a href=\"https://scratch.mit.edu/projects/${projectID}\">Link to SCRATCH Project Page</a>";
  print "        <br/>";
  print "      </div>";
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
