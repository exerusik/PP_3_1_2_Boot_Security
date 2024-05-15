document.getElementById("createUser").addEventListener("submit", function(event) {
    event.preventDefault();

    let roles = [];
    for (let i = 0; i < this.rolesForNew.options.length; i++) {
        if (this.rolesForNew.options[i].selected) {
            roles.push({
                id: 0,
                role: this.rolesForNew.options[i].value
            });
        }
    }

    const userData = {
        username: this.inputusername.value,
        lastname: this.inputlastname.value,
        age: this.inputage.value,
        email: this.inputemail.value,
        password: this.inputpassword.value,
        roles: roles
    };

    console.log(userData);

    fetch("/restAdmin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userData)
    })
    .then(response => {
        if (response.status !==201) {
            throw new Error("Ошибка HTTP: " + response.status);
        }
    })
    .then(data => {
        console.log("Пользователь успешно сохранен:");
    })
    .then(() => {
    let form = document.forms["createUser"];
                form.reset();
                $('#user-tab').click();
                getUsers();
            })
    .catch(error => {
        console.error("Ошибка при сохранении пользователя:", error);
        // Ваш код для обработки ошибки при сохранении пользователя
    });
});

