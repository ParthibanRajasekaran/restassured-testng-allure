### Create maven run configuration:

In IntelliJ,

1. Click run -> Edit configuration
2. Create/add configuration
3. Name the configuration based on the context and create the below goal in the command line field
   ```clean install site -DsuiteFile=api_regression.xml -DtestDataConfig=sampleData -DenvConfig=test```
4. Click apply and save it

<p align="center">
    <a align="middle" href="https://github.com/ParthibanRajasekaran/restassured-gherkin-testng-allure/blob/main/README.md">Take me back
      <img align="middle" alt="take me back to read me" width="45px" src="https://cdn.arrowpng.com/images/red-go-back-arrow.png" />
    </a>
</p>