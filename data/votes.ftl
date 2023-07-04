<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Who's winning?</title>
    <link rel="stylesheet" href="css/candidates.css">
</head>

<body>
<div class="container flex flex-col align-center">
    <h1>Vote for us</h1>
    <main class="flex flex-wrap align-evenly">
        <#list members as candidate>
        <div class="card">
            <form action="/vote" method="post" class="flex flex-col align-center" href="#">
                <img src="images/${candidate.photo}">
                <p>${candidate.name}</p>
                <input name="candidateId" type="hidden" value=${candidate.id}>
            </form>
        </div>
    </#list>
    </form>
    </div>
    </main>
        <div class="flex flex-col align-center" href="#">
        <a class="back flex align-center" href="/">back to main</a>
        </div>
</div>
</body>

</html>