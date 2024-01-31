from app.models.BaseModel import BaseModel


def add(model: BaseModel):
    fields = "(" + ", ".join(model.to_dict().keys()) + ")"
    values = tuple(model.to_dict().values())
    model.query(f"INSERT INTO {model.table_name} {fields} VALUES ({', '.join('?' * len(values))})", values)


def get_all(model: BaseModel):
    results = model.query(f"SELECT * FROM {model.table_name}")
    if len(results) is not None:
        return results
    else:
        return []


def get(model: BaseModel):
    return model.query(f"SELECT * FROM {model.table_name} WHERE {model.primary_key} = ?",
                       (model.primary_val,))


def update(model: BaseModel):
    set_clause = ", ".join([f"{key} = ?" for key in model.to_dict().keys()])
    values = tuple(model.to_dict().values())
    model.query(f"UPDATE {model.table_name} SET {set_clause} WHERE {model.primary_key} = ?",
                values + (model.primary_val,))


def delete(model: BaseModel):
    model.query(f"DELETE FROM {model.table_name} WHERE {model.primary_key} = ?", (model.primary_val,))


def delete_all(model: BaseModel):
    model.query(f"DELETE FROM {model.table_name}")
