import React from "react";
import {Route, Switch} from "react-router";
import SearchText from "../searchtext/SearchText";

export default class Routes extends React.Component {

    render(){
        return (
            <Switch>
                <Route exact path="/" component={SearchText} />
                <Route exact path="/messages2" component={SearchText} />
            </Switch>
        );
    }
}