import React from "react";
import SearchText from "../searchtext/SearchText";
import './Home.css';

export default class Home extends React.Component {

    render(){
        return (
            <div className="home">
                <SearchText/>
            </div>
        );
    }
}