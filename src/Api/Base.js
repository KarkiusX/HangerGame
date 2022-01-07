import axios from 'axios'

export const API = axios.create({
    baseURL:process.env.REACT_APP_SERVER_IP,
    headers: { 
        'Content-Type': 'application/json',
    } ,
    withCredentials : true, 
    timeout: 1000});