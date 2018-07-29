# gocd-jenkins-plugin [![GitHub release](https://img.shields.io/github/release/DimaGolomozy/gocd-jenkins-plugin.svg)]()

[GoCD](https://www.gocd.org/) Taks plugin to run Jenkins jobs.

Usage
-------------
Download jar from releases & place it in <go-server-location>/plugins/external & restart Go Server.  
  In the Jenkins Task editor, enter the mandatory fields: Jenkins URL, Jenkins Job name, Username and Password.  
  The `Params` field should be entered `<PARAM>=<VALUE>` with `NEWLINE` or `COMMA` delimiter.
  
  *(Go environment variables can be used)*
  
  
```
Example:
  Jenkins URL => `www.jenkins-host.com`
  Jenkins Job => `Job-Name`
  Username    => `user1`
  Password    => `pass`
  Params      => `VAR1=123,VAR2=456
                  VAR2=${GO_PIPELINE_COUNTER}`
```
  
