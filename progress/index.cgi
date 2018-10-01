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
use HTML::Entities;

#-------------------------------------------------------------
# GLOBAL VARIABLES
#
  
my $cgi = CGI->new;

my $projectID = $cgi->param("projectID");
my $projectIDFile = "../analyzedReports/project-${projectID}.txt";

my $projectURL = "https://scratch.mit.edu/projects/${projectID}/";
my $parser = HTML::TokeParser::Simple->new(url => ${projectURL});

$parser->get_tag('h2');

my $projectName = $parser->get_trimmed_text;

my $originalProjectYESNO = "YES";
my $originalProjectID = "";
my $remixedProjectID = "";

while (my $anchor = $parser->get_tag('span'))
{
  next unless defined(my $attr = $anchor->get_attr('class'));
  if ( $attr =~ /^attribute$/ )
  {
    $anchor = $parser->get_tag('a');
    my $href = $anchor->get_attr('href');
    if ( $href =~ /^\/projects\/(\d+)\/$/ )
    {
      $originalProjectYESNO = "NO";
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

#
# PROJECT VARIABLES
#

my ($scripts, $sprites, $variables, $lists, $scriptComments, $sounds, $costumes, $control, $data, $event, $looks, $moreBlocks, $motion, $operator, $pen, $sensing, $sound) = "";

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
sub parse_project
# Parse the project summary file for important information,
# such as # of scripts, variables, sounds, costumes, etc.
#-------------------------------------------------------------
{
  my $count = 0;
  my $projectFile = shift;

  open (PROJECT, "$projectFile");
  while (my $line = <PROJECT>)
  {
    chomp $line;
    my ($description, $value) = split /:/, $line;
    
    if ( length $description )
    {
      my $found = 0;
    
      if ( $description =~ /Scripts Total/ ){ $scripts = $value; $found = 1;}
      elsif ( $description =~ /Sprites Total/ ){ $sprites = $value; $found = 1;}
      elsif ( $description =~ /Variables Total/ ){ $variables = $value; $found = 1;}
      elsif ( $description =~ /Lists Total/ ){ $lists = $value; $found = 1;}
      elsif ( $description =~ /ScriptComments Total/ ){ $scriptComments = $value; $found = 1;}
      elsif ( $description =~ /Sounds Total/ ){ $sounds = $value; $found = 1;}
      elsif ( $description =~ /Costumes Total/ ){ $costumes = $value; $found = 1;}
      elsif ( $description =~ /Control Blocks/ ){ $control = $value; $found = 1;}
      elsif ( $description =~ /Data Blocks/ ){ $data = $value; $found = 1;}
      elsif ( $description =~ /Event Blocks/ ){ $event = $value; $found = 1;}
      elsif ( $description =~ /Looks Blocks/ ){ $looks = $value; $found = 1;}
      elsif ( $description =~ /More Blocks Blocks/ ){ $moreBlocks = $value; $found = 1;}
      elsif ( $description =~ /Motion Blocks/ ){ $motion = $value; $found = 1;}
      elsif ( $description =~ /Operator Blocks/ ){ $operator = $value; $found = 1;}
      elsif ( $description =~ /Pen Blocks/ ){ $pen = $value; $found = 1;}
      elsif ( $description =~ /Sensing Blocks/ ){ $sensing = $value; $found = 1;}
      elsif ( $description =~ /Sound Blocks/ ){ $sound = $value; $found = 1;}

      if ( $found == 1 && $count < 17){ print "<p>$description: $value</p>"; $count++;};
    }
  }
  close (PROJECT);
}
#-------------------------------------------------------------
sub print_header
# Begin printing the webpage. Feed the browser the
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
  print "    <link rel=\"stylesheet\" href=\"../css/report.css\">";
  print "    <link rel=\"shortcut icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">";
  print "    <link rel=\"icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">";
  print "  </head>";

  # Header to to page and background image.
  print "  <body class=\"bgimg\">";
  print "    <div class=\"bgimg\">";
  print "      <div class=\"header w3-animate-top\">";
  print "        <a href=\"/scratch\"><img src=\"../images/scratch.png\" alt=\"Title\" style=\"width:35%;height:auto;\"></a>";
  print "      </div>";
  print "      <div class=\"topnav w3-animate-left\">";
  print "        <a href=\"/scratch/\">Home</a>";
  print "        <a href=\"../analyzedReports/project-${projectID}.txt\" target=\"_blank\">Report</a>";
  print "        <a href=\"https://scratch.mit.edu/projects/${projectID}\" target=\"_blank\">Project Page</a>";
  
  if ( $remixedProjectID ne "" )
  {
    print "        <a href=\"https://scratch.mit.edu/projects/${remixedProjectID}\" target=\"_blank\">Remixed Project Page</a>";
  }

  if ( $originalProjectID ne "" )
  {
    print "        <a href=\"https://scratch.mit.edu/projects/${originalProjectID}\" target=\"_blank\">Original Project Page</a>";  
  }
  
  print "      </div>";

}
#-------------------------------------------------------------
sub print_body
# Print the first visible part of the page. Open the body tag.
# Print the page heading. If the user is an admin, state it.
#-------------------------------------------------------------
{
  print "      <div class=\"card w3-animate-bottom\">";
  print "        <h2>Project Name: ${projectName}</h2>";
  print "        <h5>ProjectID: ${projectID}</h5>";
  print "        <p>Original Project? ${originalProjectYESNO}</p>";
  print "        <p>";

  parse_project(${projectIDFile});

  if ( $remixedProjectID ne "" )
  {
    my $remixedProjectURL = "https://scratch.mit.edu/projects/${remixedProjectID}/";
    $parser = HTML::TokeParser::Simple->new(url => ${remixedProjectURL});
    $parser->get_tag('h2');

    my $remixedProjectName = $parser->get_trimmed_text; 

    print "        </p>";
    print "      </div>";
    print "      <div class=\"card w3-animate-bottom\">";
    print "        <h2>Remixed Project Name: ${remixedProjectName}</h2>";
    print "        <h5>ProjectID: ${remixedProjectID}</h5>";
    print "        <p>";

    parse_project("../analyzedReports/project-${remixedProjectID}.txt");
  }

  if ( $originalProjectID ne "" )
  {
    my $originalProjectURL = "https://scratch.mit.edu/projects/${originalProjectID}/";
    $parser = HTML::TokeParser::Simple->new(url => ${originalProjectURL});
    $parser->get_tag('h2');

    my $originalProjectName = $parser->get_trimmed_text;

    print "        </p>";
    print "      </div>";
    print "      <div class=\"card w3-animate-bottom\">";
    print "        <h2>Original Project Name: ${originalProjectName}</h2>";
    print "        <h5>ProjectID: ${originalProjectID}</h5>";
    print "        <p>";

    parse_project("../analyzedReports/project-${originalProjectID}.txt");
  }

  print "        </p>";
  print "      </div>";
}
#-------------------------------------------------------------
sub print_footer
# Prints the bottom portion of the webpage. Print a horizontal
# rule to signify the end of the page. Close the body tag and
# close the html tag.
#-------------------------------------------------------------
{
  print "      <div class=\"footer w3-animate-bottom\">";
  print "      Created & Mantained by Joseph O'Neill. Website Repository can be found <a href=\"https://github.com/oneilljo/SCRATCH\" target=\"_blank\">here</a>.";
  print "      </div>";
  print "    </div>";
  print "  </body>";
  print "</html>";
}
#-------------------------------------------------------------
