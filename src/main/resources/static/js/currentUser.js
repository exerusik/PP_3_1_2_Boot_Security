document.addEventListener("DOMContentLoaded", function() {
    fetchData();
});

function fetchData() {
    fetch('/restUser')
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
    const tableBody = document.getElementById('currentUserTable');
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
        `;
        tableBody.appendChild(row);
    });
}
