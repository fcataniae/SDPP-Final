import {createAction, props} from '@ngrx/store';

export const getVersion = createAction(
  '[Get Version]'
);

export const successGetVersion = createAction(
  '[Success get Version]',
        props<{version: string}>()

);

export const errorGetVersion = createAction(
  '[Error get Version]',
  props<{error: string}>()
);


export const getAllFiles = createAction(
  '[Get Files]'
);

export const successGetAllFiles = createAction(
  '[Success get all Files]',
  props<{files: any[]}>()
);

export const errorGetAllFiles = createAction(
  '[Error get all Files]',
  props<{error: string}>()
);
export const removeFiles = createAction(
  '[Remove Files]'
);

export const getConfiguration = createAction(
  '[Get Configuration]'
);

export const successGetConfiguration = createAction(
  '[Success get Configuration]',
  props<{config: any}>()
);

export const errorGetConfiguration = createAction(
  '[Error get Configuration]',
  props<{error: string}>()
);

export const saveConfiguration = createAction(
  '[Save Configuration]',
  props<{config: any}>()
);

export const successSaveConfiguration = createAction(
  '[Success save Configuration]',
  props<{config: any}>()
);

export const errorSaveConfiguration = createAction(
  '[Error save Configuration]',
  props<{error: string}>()
);
