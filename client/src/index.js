import React from 'react';
import ReactDOM from 'react-dom';
import './style/common.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import reportWebVitals from './reportWebVitals';
import App from "./components/App/App";
import PlacesService from "./services/PlacesService";
import {config} from 'dotenv'

config()

ReactDOM.render(
    <React.StrictMode>
        <App placesService={new PlacesService(process.env.REACT_APP_SERVER_HOST, process.env.REACT_APP_SERVER_PORT)}/>
    </React.StrictMode>,
    document.getElementById('root')
);

reportWebVitals();
