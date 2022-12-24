import dayjs from 'dayjs';
import { ICategory } from 'app/shared/model/category.model';

export interface IItem {
  id?: number;
  name?: string;
  description?: string | null;
  price?: number | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
  createdBy?: number | null;
  lastModifiedBy?: number | null;
  category?: ICategory;
}

export const defaultValue: Readonly<IItem> = {};
