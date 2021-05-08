part of 'package:epub_viewer/epub_viewer.dart';

/// Model for android EpubLocator
class EpubLocator {
  String? bookId;
  String? href;
  int? created;
  Locations? locations;

  EpubLocator({this.bookId, this.href, this.created, this.locations});

  EpubLocator.fromJson(Map<String, dynamic> json) {
    bookId = json['bookId'];
    href = json['href'];
    created = json['created'];
    locations = json['locations'] != null
        ? new Locations.fromJson(json['locations'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['bookId'] = this.bookId;
    data['href'] = this.href;
    data['created'] = this.created;
    if (this.locations != null) {
      data['locations'] = this.locations!.toJson();
    }
    return data;
  }
}

/// Model for Locations in [EpubLocator]
class Locations {
  String? cfi;

  Locations({this.cfi});

  Locations.fromJson(Map<String, dynamic> json) {
    cfi = json['cfi'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['cfi'] = this.cfi;
    return data;
  }
}
