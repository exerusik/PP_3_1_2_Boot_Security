document.addEventListener("DOMContentLoaded", function() {
    getUsers();
});

function getUsers() {
    fetch('/restAdmin')
        .then(response => response.json())
        .then(data => {
            if (!Array.isArray(data)) {
                data = [data]; // Преобразование объекта в массив
            }
            fillTable(data);
        })
        .catch(error => console.error('Ошибка загрузки данных:', error));
}

function fillTable(data) {
    const tableBody = document.getElementById('users');
    tableBody.innerHTML = ''; // Очищаем содержимое тела таблицы

    data.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.lastname}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(role => role.role).join(', ')}</td>
            <td><button class="btn-danger deleteButton" data-user-id=${user.id}>Delete</button></td>
            <td><button class="btn btn-primary editButton" data-user-id=${user.id}>Edit</button></td>
        `;
        tableBody.appendChild(row);
    });

  const editButton = document.querySelectorAll(".editButton");
  editButton.forEach(button => {
      button.addEventListener('click', function() {
          const editUserId = this.getAttribute('data-user-id');
          // Получаем данные пользователя для редактирования
          fetch(`/restUser/${editUserId}`)
              .then(response => response.json())
              .then(user => {
                  // Заполняем форму редактирования данными пользователя
                  document.getElementById('id').value = user.id;
                  document.getElementById('username').value = user.username;
                  document.getElementById('lastname').value = user.lastname;
                  document.getElementById('age').value = user.age;
                  document.getElementById('email').value = user.email;
                  document.getElementById('password').value = user.password;
                  // Открываем модальное окно редактирования
                  $('#editModal').modal('show');
              })
              .catch(error => console.error('Ошибка при получении данных пользователя:', error));
      });
  });
document.getElementById('editUserForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Предотвращаем отправку формы по умолчанию

    let roles = [];
   for (let i = 0; i < this.rolesForEdit.options.length; i++) {
       if (this.rolesForEdit.options[i].selected) {
           let roleId;
           if (this.rolesForEdit.options[i].value === "ROLE_ADMIN") {
               roleId = 1;
           } else {
               roleId = 2;
           }
           roles.push({
               id: roleId,
               role: this.rolesForEdit.options[i].value
           });
       }
   }


    // Получаем данные из формы
    const id = document.getElementById('id').value;
    const username = document.getElementById('username').value;
    const lastname = document.getElementById('lastname').value;
    const age = document.getElementById('age').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const role = roles;

    const userData = {
        id: id,
        username: username,
        lastname: lastname,
        age: age,
        email: email,
        password: password,
        roles: roles
    };

    console.log(userData);

    // Отправляем запрос на сервер для редактирования пользователя
    fetch(`/restAdmin?id=${id}`, {
        method: 'PUT', // Используем метод PUT
        headers: {
            'Content-Type': 'application/json' // Устанавливаем заголовок Content-Type
        },
        body: JSON.stringify(userData) // Преобразуем объект в строку JSON и отправляем на сервер
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Ошибка HTTP: ' + response.status);
        }
        // Если успешно, обновляем список пользователей
        getUsers();
        // Закрываем модальное окно редактирования
        $('#editModal').modal('hide');
    })
    .catch(error => console.error('Ошибка при редактировании пользователя:', error));
});


const deleteButton = document.querySelectorAll(".deleteButton");
  deleteButton.forEach(button => {
      button.addEventListener('click', function() {
          const deleteUserId = this.getAttribute('data-user-id');
          deleteUser(deleteUserId);
               })

                });



    function deleteUser(userId) {
        fetch(`/restAdmin?id=${userId}`, {
            method: 'DELETE'
        })
         .then(response => {
              if (!response.ok) {
                  throw new Error('Ошибка HTTP: ' + response.status);
              }
              // Проверяем, есть ли данные в ответе
              if (response.status !== 204) {
                  return response.json();
              }
          })
          .then(data => {
              getUsers(); // После удаления пользователя обновляем таблицу
          })
          .catch(error => console.error('Ошибка при удалении пользователя:', error));
    }
}


