const CREATE_URL = "/students/create";
const GET_ALL_URL = "/students/all";
const STUDENT_URL = "/students";

const studentForm = document.getElementById("studentForm");


// ===============================
// CREATE / UPDATE STUDENT
// ===============================

studentForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const studentId = document.getElementById("studentId").value;

    const student = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        dateOfBirth: document.getElementById("dateOfBirth").value,
        address: document.getElementById("address").value,
        grade: Number(document.getElementById("grade").value)
    };

    try {

        let response;

        // UPDATE
        if (studentId) {

            response = await fetch(`${STUDENT_URL}/${studentId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(student)
            });

        }

        // CREATE
        else {

            response = await fetch(CREATE_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(student)
            });
        }


        // DELETE returns 204, but CREATE/UPDATE return JSON
        if (response.status === 204) {

            showSuccess("Operation completed successfully.");

            resetForm();
            loadStudents();

            return;
        }


        const data = await response.json();


        if (!response.ok) {

            showError(formatError(data));

            return;
        }


        if (studentId) {
            showSuccess("Student updated successfully.");
        } else {
            showSuccess("Student created successfully.");
        }


        resetForm();
        loadStudents();

    }
    catch (error) {

        console.error(error);

        showError("Something went wrong. Please try again.");
    }
});


// ===============================
// GET ALL STUDENTS
// ===============================

async function loadStudents() {

    try {

        const response = await fetch(GET_ALL_URL);

        const students = await response.json();


        if (!response.ok) {

            showError(formatError(students));

            return;
        }


        displayStudents(students);

    }
    catch (error) {

        console.error(error);

        showError("Unable to load students.");
    }
}


// ===============================
// DISPLAY STUDENTS
// ===============================

function displayStudents(students) {

    const tableBody =
        document.getElementById("studentTableBody");

    tableBody.innerHTML = "";


    if (students.length === 0) {

        tableBody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align:center;">
                    No students found
                </td>
            </tr>
        `;

        return;
    }


    students.forEach(student => {

        const row = document.createElement("tr");

        row.innerHTML = `

            <td>${student.id}</td>

            <td>
                ${student.firstName}
                ${student.lastName}
            </td>

            <td>${student.email}</td>

            <td>${student.dateOfBirth}</td>

            <td>${student.address}</td>

            <td>${student.grade}</td>

            <td>

                <div class="action-buttons">

                    <button
                        class="edit"
                        onclick='editStudent(${JSON.stringify(student)})'>
                        Edit
                    </button>

                    <button
                        class="delete"
                        onclick="deleteStudent(${student.id})">
                        Delete
                    </button>

                </div>

            </td>
        `;

        tableBody.appendChild(row);

    });
}


// ===============================
// GET STUDENT BY ID
// ===============================

async function findStudent() {

    const studentId =
        document.getElementById("searchId").value;


    if (!studentId) {

        showError("Please enter a student ID.");

        return;
    }


    try {

        const response =
            await fetch(`${STUDENT_URL}/${studentId}`);

        const student = await response.json();


        if (!response.ok) {

            showError(formatError(student));

            return;
        }


        displayStudents([student]);

    }
    catch (error) {

        console.error(error);

        showError("Unable to find student.");
    }
}


// ===============================
// EDIT STUDENT
// ===============================

function editStudent(student) {

    document.getElementById("studentId").value =
        student.id;

    document.getElementById("firstName").value =
        student.firstName;

    document.getElementById("lastName").value =
        student.lastName;

    document.getElementById("email").value =
        student.email;

    document.getElementById("dateOfBirth").value =
        student.dateOfBirth;

    document.getElementById("address").value =
        student.address;

    document.getElementById("grade").value =
        student.grade;


    document.getElementById("formTitle").innerText =
        "Update Student";

    document.getElementById("submitButton").innerText =
        "Update Student";


    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}


// ===============================
// DELETE STUDENT
// ===============================

async function deleteStudent(studentId) {

    const confirmed =
        confirm("Are you sure you want to delete this student?");


    if (!confirmed) {
        return;
    }


    try {

        const response =
            await fetch(`${STUDENT_URL}/${studentId}`, {
                method: "DELETE"
            });


        if (!response.ok) {

            // Your DELETE normally returns 204.
            // If there is an error, try to read the response.
            let data = {};

            try {
                data = await response.json();
            } catch (e) {
                // Response may not contain JSON.
            }

            showError(formatError(data));

            return;
        }


        showSuccess("Student deleted successfully.");

        loadStudents();

    }
    catch (error) {

        console.error(error);

        showError("Unable to delete student.");
    }
}


// ===============================
// RESET FORM
// ===============================

function resetForm() {

    studentForm.reset();

    document.getElementById("studentId").value = "";

    document.getElementById("formTitle").innerText =
        "Add Student";

    document.getElementById("submitButton").innerText =
        "Add Student";
}


// ===============================
// SUCCESS MESSAGE
// ===============================

// ===============================
// POPUP MESSAGE
// ===============================

function showPopup(title, message, type) {

    const popupOverlay =
        document.getElementById("popupOverlay");

    const popupTitle =
        document.getElementById("popupTitle");

    const popupMessage =
        document.getElementById("popupMessage");

    const popupIcon =
        document.getElementById("popupIcon");


    popupTitle.innerText = title;

    popupMessage.innerText = message;


    // SUCCESS
    if (type === "success") {

        popupIcon.innerText = "✓";

        popupIcon.style.background = "#e8f5e9";

        popupIcon.style.color = "#2e7d32";

    }

    // ERROR
    else {

        popupIcon.innerText = "✕";

        popupIcon.style.background = "#ffebee";

        popupIcon.style.color = "#c62828";

    }


    popupOverlay.style.display = "flex";
}


// ===============================
// SUCCESS POPUP
// ===============================

function showSuccess(message) {

    showPopup(
        "Success",
        message,
        "success"
    );
}


// ===============================
// ERROR POPUP
// ===============================

function showError(message) {

    showPopup(
        "Error",
        message,
        "error"
    );
}


// ===============================
// CLOSE POPUP
// ===============================

function closePopup() {

    document.getElementById("popupOverlay")
        .style.display = "none";
}


// ===============================
// OK BUTTON
// ===============================

document
    .getElementById("popupOkayButton")
    .addEventListener("click", closePopup);


// ===============================
// CLICK OUTSIDE POPUP
// ===============================

document
    .getElementById("popupOverlay")
    .addEventListener("click", function (event) {

        if (event.target === this) {

            closePopup();

        }

    });


// ===============================
// FORMAT BACKEND ERROR
// ===============================

function formatError(data) {

    if (!data) {
        return "Something went wrong.";
    }


    if (typeof data === "string") {
        return data;
    }


    if (data.error) {
        return data.error;
    }


    if (data.message) {
        return data.message;
    }


    // Validation errors
    if (typeof data === "object") {

        return Object.values(data).join(" | ");
    }


    return "Something went wrong.";
}


// ===============================
// LOAD STUDENTS WHEN PAGE OPENS
// ===============================

document.addEventListener("DOMContentLoaded", () => {

    loadStudents();

});