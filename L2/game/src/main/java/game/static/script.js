const form = document.getElementById("form")
const textOutput = document.getElementById("header")

const responseTexts = {
	HIGHER: guesses => `Nope, guess higher<br> You have made ${guesses} guess(es)<br>`,
	LOWER: guesses => `Nope, guess lower<br> You have made ${guesses} guess(es)<br>`,
	WIN: () => `You made it!!!<br>`,
}

form.addEventListener("submit", e => {
	e.preventDefault()
	const guess = new FormData(e.target).get("guess")
	if (!guess) return

	fetch("http://localhost:8090/game/guess", {
		method: "POST",
		body: guess,
		credentials: "include",
	})
		.then(res => (res.ok ? res.json() : console.error(`${res.status}: ${res.statusText}`)))
		.then(data => {
			if (!data) return
			textOutput.innerHTML = responseTexts[data.response](data.numberOfGuesses)
			document.querySelector("input[type=text]").value = ""
		})
})

fetch("http://localhost:8090/game/guess", {
	credentials: "include",
})
	.then(res => (res.ok ? res.json() : console.error(`${res.status}: ${res.statusText}`)))
	.then(data => {
		if (!data) return (textOutput.innerHTML = `Welcome to the Number Guess Game. Guess a number between 1 and 100.`)
		textOutput.innerHTML = responseTexts[data.response](data.numberOfGuesses)
	})
