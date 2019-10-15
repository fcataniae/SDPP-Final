import {
  createReducer,
  on
} from '@ngrx/store';
import {ConfigurationComponent} from "../components/configuration/configuration.component";
import * as Action from "./app.actions";


export interface MenuState {
  enlaces : any[];
  brand : any;
  configuration: any;
  version: string;
  error: any
}

const menuInitialState: MenuState = {
  enlaces: [{view: 'Subir archivos', url: '/uploads'},
    {view: 'Compartidos', url: '/shareds'},
    {view: 'Busqueda avanzada', url: '/searchs'}],
  brand:  {url: '/', logoUrl: './assets/logo.jpg', logo: 'SD&PP'},
  configuration:{component: ConfigurationComponent},
  version: '',
  error: ''
};


export const _menuReducer = createReducer(menuInitialState,
  on(Action.getVersion, state => ({...state})),
  on(Action.successGetVersion, (state, action)=> ({...state, version: action.version})),
  on(Action.errorGetVersion, (state, action) => ({...state, error: ({...action.error})}))
);


export interface FileState{
  files: any[];
  error: any;
}

const fileInitialState : FileState = {
  files: [],
  error: ''
}

export const _fileReducer = createReducer(fileInitialState,
  on(Action.getAllFiles, state => ({...state})),
  on(Action.successGetAllFiles, (state, action)=> ({...state, files:({...action.files})})),
  on(Action.errorGetAllFiles, (state, action) => ({...state, error: ({...action.error})}))
);


export function menuReducer(state, action) {
  return _menuReducer(state, action);
}

export function fileReducer(state, action) {
  return _fileReducer(state, action);
}
