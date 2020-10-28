<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Wordscope</title>
</head>

<body>
<form action="/asciiSum">
    <label for="q">Enter a string to sum:</label>
    <input type="text" id="q" name="q">
    <input type="submit" value="Sum">
</form>
<#if asciiSum?? && asciiSum.input != "">
    <div>
        <p>Input: ${asciiSum.input}</p>
        <p>Sum: ${asciiSum.sum}</p>
    </div>
</#if>

</body>
</html>