import { Moment } from 'moment';

export interface IRemesas {
    id?: number;
    empresa?: string;
    tasa?: string;
    fechaAct?: Moment;
}

export class Remesas implements IRemesas {
    constructor(public id?: number, public empresa?: string, public tasa?: string, public fechaAct?: Moment) {}
}
