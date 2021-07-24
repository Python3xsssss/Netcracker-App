interface SetItem {
  equals(other: SetItem): boolean;
}

export class MySet<T extends SetItem> extends Set<T> {
  add(value: T): this {
    let found = false;
    this.forEach(item => {
      if (value.equals(item)) {
        found = true;
      }
    });

    if (!found) {
      super.add(value);
    }

    return this;
  }
}
