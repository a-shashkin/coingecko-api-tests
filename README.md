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
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/IntelliJ_IDEA_Icon.svg/1200px-IntelliJ_IDEA_Icon.svg.png" width="40" height="40"><br>IntelliJ IDEA</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://cdn-icons-png.flaticon.com/512/226/226777.png" width="40" height="40"><br>Java</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://static-00.iconduck.com/assets.00/gradle-icon-256x256-jq2wrvfo.png" width="40" height="40"><br>Gradle</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://junit.org/junit5/assets/img/junit5-logo.png" width="40" height="40"><br>JUnit5</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://media.trustradius.com/product-logos/M1/My/B8NQDTOWGI16.PNG" width="40" height="40"><br>Rest-Assured</td>
</tr>
<tr>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Jenkins_logo.svg/1200px-Jenkins_logo.svg.png" width="40" height="40"><br>Jenkins</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" width="40" height="40"><br>Github</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://img.stackshare.io/service/40438/default_a9d9f8f8546d65b5f12a32106e6d03e6921e11fa.png" width="40" height="40"><br>Allure TestOps</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://logowik.com/content/uploads/images/jira2966.logowik.com.webp" width="40" height="40"><br>Jira</td>
<td align="center"><src="https://www.jetbrains.com/idea/"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Telegram_logo.svg/2048px-Telegram_logo.svg.png" width="40" height="40"><br>Telegram Bot</td>
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
