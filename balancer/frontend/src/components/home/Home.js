import React from 'react';
import SearchText from '../searchtext/SearchText';
import './Home.css';
import logo from '../../logo.svg';

export default class Home extends React.Component {

    render(){
        return (
            <div className="home">
                <div className="ml-auto mr-auto p-xl-3" >
                    <img className=" rounded mx-auto d-block " src={logo} alt="logo"/>
                </div>
                <SearchText/>
            </div>
        );
    }
}