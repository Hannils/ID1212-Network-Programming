const form = document.getElementById("form")


form.addEventListener("submit", e => {
    e.preventDefault()
    const guess = new FormData(e.target).get("guess")
    if (!guess) return

    fetch("http://localhost:8090", {
        method: 'POST',
        body: guess,
        credentials: 'include',
    }).then(res =>
        res.ok ? res.text() : console.error(`${res.status}: ${res.statusText}`)
    ).then(console.log)
    location.reload();
})