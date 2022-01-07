import axios from 'axios'

export const API = axios.create({
    baseURL: "http://158.129.21.109:8080",
    headers: { 
        'Content-Type': 'application/json',
    } ,
    withCredentials : true, 
    timeout: 1000});