# Automated API tests for [JSONPlaceholder web site](https://jsonplaceholder.typicode.com)

<img width="1436" alt="JSONPlaceholder site" src="https://user-images.githubusercontent.com/47101779/210243898-6f985e7b-6760-4166-b554-fe04860d9a8b.png">

## Realized automated API tests:
- Tests related to retrieval, creation, editing and deleting of users;
- Tests related to retrieval, creation, editing and deleting of albums;
- Tests related to retrieval, creation, editing and deleting of photos;
- Tests related to retrieval, creation, editing and deleting of to-dos;
- Tests related to retrieval, creation, editing and deleting of posts;
- Tests related to retrieval of nested data: comments from posts; photos from albums; albums, to-dos and posts from users.

## Technology stack:
<table>
<tbody>
<tr>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/Intelij_IDEA.svg" width="40" height="40"><br>IntelliJ IDEA</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/Java.svg" width="40" height="40"><br>Java</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/Gradle.svg" width="40" height="40"><br>Gradle</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/JUnit5.svg" width="40" height="40"><br>JUnit5</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/Rest-Assured.svg" width="40" height="40"><br>Rest-Assured</td>
</tr>
<tr>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/Jenkins.svg" width="40" height="40"><br>Jenkins</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/Github.svg" width="40" height="40"><br>Github</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/Allure_EE.svg" width="40" height="40"><br>Allure TestOps</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/Jira.svg" width="40" height="40"><br>Jira</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://starchenkov.pro/qa-guru/img/skills/Telegram.svg" width="40" height="40"><br>Telegram Bot</td>
</tr>
</tbody>
</table>

## Local terminal launch commands
config.properties file must be locally created and contain the following line:
```bash
baseUrl=https://jsonplaceholder.typicode.com
```
### Launch all tests:
```bash
gradle clean test
```
### Launch only tests related to "photos" entity:
```bash
gradle clean photos_tests
```
### Launch only tests that use HTTP method GET:
```bash
gradle clean get_request_tests
```
### Build Allure report:
```bash
allure serve build/allure-results
```
List of available tasks includes:
- test
- albums_tests
- photos_tests
- posts_tests
- todos_tests
- users_tests
- get_request_tests
- post_request_tests
- put_request_tests
- patch_request_tests
- delete_request_tests
- non_get_request_tests (i.e., tests for requests using POST, PUT, PATCH or DELETE methods)

## Parameters for [Jenkins build](https://jenkins.autotests.cloud/job/C08-AShashkin-JSONPlaceholder-tests/)

<img width="1436" alt="Jenkins build options" src="https://user-images.githubusercontent.com/47101779/210244004-f5c3446e-c572-4835-9a36-6cb19489307e.png">

### Changing test data in Jenkins
Repository contains .json files in resources/testData folder, that are used as sources of expected result and request body.

Should you require different test data, you can use Jenkins build options to overwrite data with your own. Example is on screenshot:

<img width="1436" alt="How to overwrite data" src="https://user-images.githubusercontent.com/47101779/210244475-5ead1c83-b12b-4527-bfff-7fbd59664a43.png">

## [Allure TestOps integration](https://allure.autotests.cloud/project/1770/dashboards)
### Test case tree in TestOps

<img width="1436" alt="Test case tree" src="https://user-images.githubusercontent.com/47101779/210244572-8f3f78b8-bb8a-4d35-990a-aefe20ba21e8.png">

### Launch result

<img width="1436" alt="Launch result" src="https://user-images.githubusercontent.com/47101779/210244619-34cc2d91-dfbb-4d62-a04d-bc79b664d918.png">

## Telegram notification

<img width="760" alt="Telegram notification" src="https://user-images.githubusercontent.com/47101779/210245155-a24b2be7-af45-4d06-a757-2f3044cbfba9.png" align="center">
