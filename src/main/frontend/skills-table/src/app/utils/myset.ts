interface SetItem {
  equals(other: SetItem): boolean;
}

// this is the implementation of set in TypeScript
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
