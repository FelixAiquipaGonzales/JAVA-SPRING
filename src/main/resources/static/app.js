const API_URL = '/contactos';

// DOM Elements
const contactsTableBody = document.getElementById('contactsTableBody');
const modal = document.getElementById('contactModal');
const modalTitle = document.getElementById('modalTitle');
const contactForm = document.getElementById('contactForm');
const addBtn = document.getElementById('addWaitBtn'); // Renamed to avoid confusion if needed, but I'll use addBtn
const closeBtn = document.getElementById('closeBtn');
const cancelBtn = document.getElementById('cancelBtn');

// State
let isEditing = false;
let currentId = null;

// Fetch and Display Contacts
async function fetchContacts() {
    try {
        const response = await fetch(`${API_URL}/getAll`);
        if (!response.ok) throw new Error('Failed to fetch contacts');
        const contacts = await response.json();
        renderContacts(contacts);
    } catch (error) {
        console.error('Error:', error);
        // Show error in UI
    }
}

function renderContacts(contacts) {
    contactsTableBody.innerHTML = '';
    if (contacts.length === 0) {
        contactsTableBody.innerHTML = '<tr><td colspan="9" class="loading">No hay contactos registrados.</td></tr>';
        return;
    }

    contacts.forEach(contact => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${contact.id}</td>
            <td>${contact.nombre}</td>
            <td>${contact.apellidoPat}</td>
            <td>${contact.apellidoMat}</td>
            <td>${contact.sexo}</td>
            <td>${contact.dni || '-'}</td>
            <td>${contact.ruc}</td>
            <td>${contact.email}</td>
            <td>${contact.telefono || '-'}</td>
            <td>
                <div class="actions">
                    <button class="btn btn-icon btn-edit" onclick="editContact(${contact.id})" title="Editar">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-icon btn-danger" onclick="deleteContact(${contact.id})" title="Eliminar">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        `;
        contactsTableBody.appendChild(tr);
    });
}

// Open Modal
function openModal(editing = false, contact = null) {
    isEditing = editing;
    modal.classList.add('open');
    if (editing && contact) {
        modalTitle.textContent = 'Editar Contacto';
        currentId = contact.id;
        document.getElementById('nombre').value = contact.nombre;
        document.getElementById('apellidoPat').value = contact.apellidoPat;
        document.getElementById('apellidoMat').value = contact.apellidoMat;
        document.getElementById('sexo').value = contact.sexo;
        document.getElementById('dni').value = contact.dni || '';
        document.getElementById('ruc').value = contact.ruc;
        document.getElementById('email').value = contact.email;
        document.getElementById('telefono').value = contact.telefono || '';
    } else {
        modalTitle.textContent = 'Nuevo Contacto';
        currentId = null;
        contactForm.reset();
    }
}

// Close Modal
function closeModal() {
    modal.classList.remove('open');
    contactForm.reset();
    isEditing = false;
    currentId = null;
}

// Add/Edit Contact
contactForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = {
        nombre: document.getElementById('nombre').value,
        apellidoPat: document.getElementById('apellidoPat').value,
        apellidoMat: document.getElementById('apellidoMat').value,
        sexo: document.getElementById('sexo').value,
        dni: document.getElementById('dni').value,
        ruc: document.getElementById('ruc').value,
        email: document.getElementById('email').value,
        telefono: document.getElementById('telefono').value
    };

    try {
        let response;
        if (isEditing) {
            // Need to set ID for VO usually? Depending on how copyProperties works.
            // But controller uses PathVariable ID.
            formData.id = currentId; // Just in case
            response = await fetch(`${API_URL}/getId/${currentId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });
        } else {
            response = await fetch(`${API_URL}/save`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });
        }

        if (!response.ok) throw new Error('Operation failed');

        closeModal();
        fetchContacts();
    } catch (error) {
        console.error('Error:', error);
        alert('Error al guardar contacto. Verifique los datos.');
    }
});

// Edit Contact Click Handler
window.editContact = async (id) => {
    try {
        const response = await fetch(`${API_URL}/getId/${id}`);
        if (!response.ok) throw new Error('Failed to fetch contact details');
        const contact = await response.json();
        openModal(true, contact);
    } catch (error) {
        console.error(error);
    }
};

// Delete Contact
window.deleteContact = async (id) => {
    if (!confirm('¿Está seguro de eliminar este contacto?')) return;

    try {
        const response = await fetch(`${API_URL}/delete/${id}`, {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('Failed to delete');
        fetchContacts();
    } catch (error) {
        console.error(error);
        alert('Error al eliminar contacto');
    }
};

// Event Listeners
document.getElementById('addBtn').addEventListener('click', () => openModal(false));
closeBtn.addEventListener('click', closeModal);
cancelBtn.addEventListener('click', closeModal);

// Close on outside click
modal.addEventListener('click', (e) => {
    if (e.target === modal) closeModal();
});

// Initial Load
fetchContacts();
