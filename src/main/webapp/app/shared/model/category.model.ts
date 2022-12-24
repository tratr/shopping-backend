import dayjs from 'dayjs';
import { IItem } from 'app/shared/model/item.model';

export interface ICategory {
  id?: number;
  name?: string;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
  createdBy?: number | null;
  lastModifiedBy?: number | null;
  items?: IItem[] | null;
}

export const defaultValue: Readonly<ICategory> = {};
