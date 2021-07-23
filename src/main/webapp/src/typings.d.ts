interface SetItem {
  equals(other: SetItem): boolean;
}
declare class MySet<T extends SetItem> extends Set<T> {
  add(value: T): this;
}
