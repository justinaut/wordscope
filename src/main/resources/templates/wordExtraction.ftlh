<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Wordscope</title>
</head>

<body>
<form action="/extractWords">
    <label for="q">Enter a word, or word jumble, to find words:</label>
    <input type="text" id="q" name="q">
    <input type="submit" value="Extract">
</form>
<#if wordExtraction?? && wordExtraction.input != "">
<div>
    <p>Input: ${wordExtraction.input}</p>
    <p>List:</p>
    <#list wordExtraction.words as item>
    <p>${item}</p>
    </#list>
</div>
</#if>

</body>
</html>