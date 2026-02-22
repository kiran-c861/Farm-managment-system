/**
 * API Helper - Centralized HTTP client with JWT injection.
 * All API calls go through these functions.
 */
const API_BASE = 'http://localhost:8080';

function getToken() {
    return localStorage.getItem('token');
}

function getHeaders(isJson = true) {
    const headers = {};
    if (isJson) headers['Content-Type'] = 'application/json';
    const token = getToken();
    if (token) headers['Authorization'] = 'Bearer ' + token;
    return headers;
}

async function handleResponse(res) {
    const contentType = res.headers.get('content-type') || '';
    const isJson = contentType.includes('application/json');
    const data = isJson ? await res.json() : await res.text();

    if (!res.ok) {
        if (res.status === 401) {
            // Token expired or invalid - redirect to login
            localStorage.clear();
            window.location.href = 'index.html';
        }
        throw new Error((data && data.message) || `HTTP Error: ${res.status}`);
    }
    return data;
}

async function apiGet(path) {
    const res = await fetch(API_BASE + path, { headers: getHeaders() });
    return handleResponse(res);
}

async function apiPost(path, body) {
    const res = await fetch(API_BASE + path, {
        method: 'POST',
        headers: getHeaders(),
        body: JSON.stringify(body)
    });
    return handleResponse(res);
}

async function apiPut(path, body) {
    const res = await fetch(API_BASE + path, {
        method: 'PUT',
        headers: getHeaders(),
        body: JSON.stringify(body)
    });
    return handleResponse(res);
}

async function apiDelete(path) {
    const res = await fetch(API_BASE + path, {
        method: 'DELETE',
        headers: getHeaders()
    });
    if (res.status === 204) return null;
    return handleResponse(res);
}

// Get current user from localStorage
function getCurrentUser() {
    try { return JSON.parse(localStorage.getItem('user')) || {}; }
    catch { return {}; }
}

function getFarmId() {
    return localStorage.getItem('farmId') || '1';
}
