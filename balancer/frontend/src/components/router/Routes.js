import React from "react";
import {Route, Switch} from "react-router";
import SearchText from "../searchtext/SearchText";
import NotFound from "../error/NotFound";

export default class Routes extends React.Component {

    render(){
        return (
            <Switch>
                <Route exact path="/" component={SearchText} />
                <Route path="/messages2" component={SearchText} />
                <Route path="*" component={NotFound} />
            </Switch>
        );
    }
}