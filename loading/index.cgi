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
use HTML::TokeParser::Simple;

#-------------------------------------------------------------
# GLOBAL VARIABLES
#
  
my $cgi = CGI->new;

my $projectURL = $cgi->param("projectURL");
$projectURL =~ /^(?:(?:http(?:s)??:\/\/)?scratch\.mit\.edu\/projects\/)?([0-9]+)\/?(.+)?$/;

my $projectID = $1;
my $parser;

$projectURL = "https://scratch.mit.edu/projects/${projectID}/";
eval
{
  $parser = HTML::TokeParser::Simple->new(url => ${projectURL});
}
or do
{
  $parser = "Failed";
};

print STDERR "ProjectID: $projectID\n";

my $originalProjectID = "";
my $remixedProjectID = "";

if ($parser ne "Failed")
{
  while (my $anchor = $parser->get_tag('span'))
  {
    next unless defined(my $attr = $anchor->get_attr('class'));
    if ( $attr =~ /^attribute$/ )
    {
      $anchor = $parser->get_tag('a');
      my $href = $anchor->get_attr('href');
      if ( $href =~ /^\/projects\/(\d+)\/$/ )
      {
        $originalProjectID = $1;

        while ($anchor = $parser->get_tag('span'))
        {
          next unless defined(my $attr = $anchor->get_attr('class'));
          if ( $attr =~ /^attribute$/ )
          {
            $anchor = $parser->get_tag('a');
            my $href = $anchor->get_attr('href');
            if ( $href =~ /^\/projects\/(\d+)\/$/ )
            {
              $remixedProjectID = $originalProjectID;
              $originalProjectID = $1;

              last;
            }
          }
        }
        last;
      }
    }
  }
}

#while (my $anchor = $parser->get_tag('a'))
#{
#  next unless defined(my $href = $anchor->get_attr('href'));
#  if ( $href =~ /^\/projects\/(\d+)\/$/ )
#  {
#    $originalProjectID = $1;
#  }
#}

#my $originalProjectID = `curl -sS https://scratch.mit.edu/projects/${projectID}/ | grep -o '<a href=\"/projects/[[:digit:]]\{1,\}/\">' | tail -n 1 | grep -o '[[:digit:]]\{1,\}'`;

#
# END GLOBAL VARIABLES
#-------------------------------------------------------------
# MAIN
#

print_header();

# If the project is publicly shared
if ($parser ne "Failed") 
{ 
  print_success_body();
}
# Otherwise, print a failure message
else 
{ 
  print_failed_body(); 
}

print_footer();

# Don't try to download the project if it isn't publicly shared
if ($parser ne "Failed")
{
  download(${projectID});

  if ( $originalProjectID ne "" ) 
  {
    download(${originalProjectID});
  }

  if ( $remixedProjectID ne "" )
  {
    download(${remixedProjectID});
  }
}

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

  print "<html>\n";
  print "  <head>\n";
  print "    <title>SCRATCH Thesis Project</title>\n";
  print "    <meta charset=\"UTF-8\">\n";
  print "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
  print "    <link rel=\"stylesheet\" href=\"../css/format.css\">\n";
  print "    <link rel=\"stylesheet\" href=\"../css/report.css\">\n";
  print "    <link rel=\"shortcut icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">\n";
  print "    <link rel=\"icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">\n";
  print "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n";
  print "  </head>\n";
}
#-------------------------------------------------------------
sub print_success_body
# Print the first visible part of the page. Open the body tag.
# Print the page heading. If the user is an admin, state it.
#-------------------------------------------------------------
{
  # Header to to page and background image.
  print "  <body onload=\"document.download.submit()\">\n";
  print "    <div class=\"display-container bgimg text-white\">\n";
  print "      <div class=\"display-middle center\">\n";
  print "        <img class= \"animate-top\" src=\"../images/scratch.png\" alt=\"Title\">\n";
  print "        </br>\n";
  print "        <label class=\"animate-bottom padding-small\" for=\"download\">Downloading and processing your project...</label>\n";
  print "        </br>\n";
  print "        </br>\n";
  print "        <img class=\"animate-bottom\" style=\"width:50px;height:50px;\"  src=\"../images/loading.gif\" alt=\"Loading\">\n";
  print "        <form name=\"download\" method=\"post\" action=\"/scratch/progress/index.cgi\">\n";
  print "          <input type=\"hidden\" name=\"projectID\" id=\"projectID\" value=\"${projectID}\" />\n";
  print "        </form>\n";
  print "      </div>\n";
}
#-------------------------------------------------------------
sub print_failed_body
# Print the first visible part of the page. Open the body tag.
# Print the page heading. If the user is an admin, state it.
#-------------------------------------------------------------
{
  # Header to to page and background image.
  print "  <body>\n";
  print "    <div class=\"display-container bgimg text-white\">\n";
  print "      <div class=\"display-middle center\">\n";
  print "        <img class= \"animate-top\" src=\"../images/scratch.png\" alt=\"Title\">\n";
  print "        <img class= \"animate-left\" src=\"../images/sad_cat.png\" alt=\"Sad Cat\" style=\"height:200; width:200;\">\n";
  print "        <h3 class= \"animate-right\">Uh oh! This project either isn't publicly shared or doesn't exist!</h3>\n";
  print "        <h4 class= \"animate-bottom\">If this is your project, click <a href=\"${projectURL}\" target=\"_blank\">here</a> and share the project.</h4>\n";
  print "        <h5 class= \"animate-bottom\">Once the project has been shared, click below to try again.</h5>\n";
  print "        <form method=\"post\" action=\"/scratch/loading/index.cgi\">\n";
  print "          <input type=\"hidden\" id=\"projectURL\" name=\"projectURL\" value=\"${projectID}\">\n";
  print "          <input type=\"submit\" class=\"button animate-bottom round-xxlarge red padding-large\" value=\"Analyze\">\n";
  print "        </form>\n";
  print "      </div>\n";
}
#-------------------------------------------------------------
sub print_footer
# Prints the bottom portion of the webpage. Print a horizontal
# rule to signify the end of the page. Close the body tag and
# close the html tag.
#-------------------------------------------------------------
{
  print "      <div class=\"display-bottomleft padding-small tiny\">\n";
  print "      Created & Mantained by Joseph O'Neill. Website Repository can be found <a href=\"https://github.com/oneilljo/SPAE\" target=\"_blank\">here</a>.\n";
  print "      </div>\n";
  print "    </div>\n";
  print "  </body>\n";
  print "</html>\n";
}
#-------------------------------------------------------------
sub download
# Downloads the project on the system side, refreshing the 
# loaded page on completion.
#-------------------------------------------------------------
{
  my $downloadID = shift;

  system("./projectdownloader.py ${downloadID} > /dev/null");
  system("chmod 755 project-${downloadID}.sb2");
  system("chown apache:apache project-${downloadID}.sb2");
  system("cp project-${downloadID}.sb2 ../savedProjects");
  system("mv -f project-${downloadID}.sb2 ../SCATT/submissions/project-${downloadID}.sb2");
  system("sleep 1");
  system("/usr/bin/java -jar ../SCATT/Scatt.jar > /dev/null");
  system("rm -f ../SCATT/submissions/project-${downloadID}.sb2");
  system("rm -f zips/${downloadID}.zip");
  system("mv unzips/project-${downloadID}/project.json ../savedReports/project-${downloadID}");
  system("rm -rf unzips/${downloadID}");
}
#-------------------------------------------------------------
