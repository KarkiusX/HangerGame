import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import {Game} from './Game/GameCanvas'
import reportWebVitals from './reportWebVitals';

import { BrowserRouter, Routes,Route, HashRouter } from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.min.css';

ReactDOM.render(
  <React.StrictMode>
      <HashRouter>
        <Routes>
            <Route path="/" element={<App />} />
            <Route path="/game" element={<Game/>} />
        </Routes>
      </HashRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
